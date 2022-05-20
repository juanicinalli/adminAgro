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
import { IInsumos } from 'app/shared/model/insumos.model';
import { getEntity, updateEntity, createEntity, reset } from './insumos.reducer';

export const InsumosUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const manejos = useAppSelector(state => state.manejo.entities);
  const insumosEntity = useAppSelector(state => state.insumos.entity);
  const loading = useAppSelector(state => state.insumos.loading);
  const updating = useAppSelector(state => state.insumos.updating);
  const updateSuccess = useAppSelector(state => state.insumos.updateSuccess);
  const handleClose = () => {
    props.history.push('/insumos');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getManejos({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...insumosEntity,
      ...values,
      manejo: manejos.find(it => it.id.toString() === values.manejo.toString()),
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
          ...insumosEntity,
          manejo: insumosEntity?.manejo?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="adminAgroApp.insumos.home.createOrEditLabel" data-cy="InsumosCreateUpdateHeading">
            <Translate contentKey="adminAgroApp.insumos.home.createOrEditLabel">Create or edit a Insumos</Translate>
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
                  id="insumos-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('adminAgroApp.insumos.categoria')}
                id="insumos-categoria"
                name="categoria"
                data-cy="categoria"
                type="text"
              />
              <ValidatedField
                label={translate('adminAgroApp.insumos.nombre')}
                id="insumos-nombre"
                name="nombre"
                data-cy="nombre"
                type="text"
              />
              <ValidatedField
                label={translate('adminAgroApp.insumos.precioPorUnidad')}
                id="insumos-precioPorUnidad"
                name="precioPorUnidad"
                data-cy="precioPorUnidad"
                type="text"
              />
              <ValidatedField
                label={translate('adminAgroApp.insumos.unidad')}
                id="insumos-unidad"
                name="unidad"
                data-cy="unidad"
                type="text"
              />
              <ValidatedField
                id="insumos-manejo"
                name="manejo"
                data-cy="manejo"
                label={translate('adminAgroApp.insumos.manejo')}
                type="select"
              >
                <option value="" key="0" />
                {manejos
                  ? manejos.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/insumos" replace color="info">
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

export default InsumosUpdate;
