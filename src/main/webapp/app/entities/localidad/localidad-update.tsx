import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IProvincia } from 'app/shared/model/provincia.model';
import { getEntities as getProvincias } from 'app/entities/provincia/provincia.reducer';
import { ILocalidad } from 'app/shared/model/localidad.model';
import { getEntity, updateEntity, createEntity, reset } from './localidad.reducer';

export const LocalidadUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const provincias = useAppSelector(state => state.provincia.entities);
  const localidadEntity = useAppSelector(state => state.localidad.entity);
  const loading = useAppSelector(state => state.localidad.loading);
  const updating = useAppSelector(state => state.localidad.updating);
  const updateSuccess = useAppSelector(state => state.localidad.updateSuccess);
  const handleClose = () => {
    props.history.push('/localidad');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getProvincias({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...localidadEntity,
      ...values,
      provincia: provincias.find(it => it.id.toString() === values.provincia.toString()),
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
          ...localidadEntity,
          provincia: localidadEntity?.provincia?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="adminAgroApp.localidad.home.createOrEditLabel" data-cy="LocalidadCreateUpdateHeading">
            <Translate contentKey="adminAgroApp.localidad.home.createOrEditLabel">Create or edit a Localidad</Translate>
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
                  id="localidad-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('adminAgroApp.localidad.nombre')}
                id="localidad-nombre"
                name="nombre"
                data-cy="nombre"
                type="text"
              />
              <ValidatedField
                label={translate('adminAgroApp.localidad.direccion')}
                id="localidad-direccion"
                name="direccion"
                data-cy="direccion"
                type="text"
              />
              <ValidatedField
                label={translate('adminAgroApp.localidad.codigoPostal')}
                id="localidad-codigoPostal"
                name="codigoPostal"
                data-cy="codigoPostal"
                type="text"
              />
              <ValidatedField
                id="localidad-provincia"
                name="provincia"
                data-cy="provincia"
                label={translate('adminAgroApp.localidad.provincia')}
                type="select"
              >
                <option value="" key="0" />
                {provincias
                  ? provincias.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/localidad" replace color="info">
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

export default LocalidadUpdate;
