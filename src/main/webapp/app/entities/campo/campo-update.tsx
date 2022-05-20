import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IManejo } from 'app/shared/model/manejo.model';
import { getEntities as getManejos } from 'app/entities/manejo/manejo.reducer';
import { ILocalidad } from 'app/shared/model/localidad.model';
import { getEntities as getLocalidads } from 'app/entities/localidad/localidad.reducer';
import { ICampo } from 'app/shared/model/campo.model';
import { getEntity, updateEntity, createEntity, reset } from './campo.reducer';

export const CampoUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const manejos = useAppSelector(state => state.manejo.entities);
  const localidads = useAppSelector(state => state.localidad.entities);
  const campoEntity = useAppSelector(state => state.campo.entity);
  const loading = useAppSelector(state => state.campo.loading);
  const updating = useAppSelector(state => state.campo.updating);
  const updateSuccess = useAppSelector(state => state.campo.updateSuccess);
  const handleClose = () => {
    props.history.push('/campo');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getManejos({}));
    dispatch(getLocalidads({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...campoEntity,
      ...values,
      manejo: manejos.find(it => it.id.toString() === values.manejo.toString()),
      localidad: localidads.find(it => it.id.toString() === values.localidad.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          ...campoEntity,
          manejo: campoEntity?.manejo?.id,
          localidad: campoEntity?.localidad?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="adminAgroApp.campo.home.createOrEditLabel" data-cy="CampoCreateUpdateHeading">
            <Translate contentKey="adminAgroApp.campo.home.createOrEditLabel">Create or edit a Campo</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="campo-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('adminAgroApp.campo.numeroDeLote')}
                id="campo-numeroDeLote"
                name="numeroDeLote"
                data-cy="numeroDeLote"
                type="text"
              />
              <ValidatedField
                label={translate('adminAgroApp.campo.superficie')}
                id="campo-superficie"
                name="superficie"
                data-cy="superficie"
                type="text"
              />
              <ValidatedField
                label={translate('adminAgroApp.campo.tenencia')}
                id="campo-tenencia"
                name="tenencia"
                data-cy="tenencia"
                type="text"
              />
              <ValidatedField
                label={translate('adminAgroApp.campo.cultivo')}
                id="campo-cultivo"
                name="cultivo"
                data-cy="cultivo"
                type="text"
              />
              <ValidatedField id="campo-manejo" name="manejo" data-cy="manejo" label={translate('adminAgroApp.campo.manejo')} type="select">
                <option value="" key="0" />
                {manejos
                  ? manejos.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="campo-localidad"
                name="localidad"
                data-cy="localidad"
                label={translate('adminAgroApp.campo.localidad')}
                type="select"
              >
                <option value="" key="0" />
                {localidads
                  ? localidads.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/campo" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default CampoUpdate;
