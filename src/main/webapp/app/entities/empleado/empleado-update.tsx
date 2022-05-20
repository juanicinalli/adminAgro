import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ITrabajo } from 'app/shared/model/trabajo.model';
import { getEntities as getTrabajos } from 'app/entities/trabajo/trabajo.reducer';
import { IEmpleado } from 'app/shared/model/empleado.model';
import { getEntity, updateEntity, createEntity, reset } from './empleado.reducer';

export const EmpleadoUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const trabajos = useAppSelector(state => state.trabajo.entities);
  const empleadoEntity = useAppSelector(state => state.empleado.entity);
  const loading = useAppSelector(state => state.empleado.loading);
  const updating = useAppSelector(state => state.empleado.updating);
  const updateSuccess = useAppSelector(state => state.empleado.updateSuccess);
  const handleClose = () => {
    props.history.push('/empleado' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getTrabajos({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...empleadoEntity,
      ...values,
      trabajo: trabajos.find(it => it.id.toString() === values.trabajo.toString()),
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
          ...empleadoEntity,
          trabajo: empleadoEntity?.trabajo?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="adminAgroApp.empleado.home.createOrEditLabel" data-cy="EmpleadoCreateUpdateHeading">
            <Translate contentKey="adminAgroApp.empleado.home.createOrEditLabel">Create or edit a Empleado</Translate>
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
                  id="empleado-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('adminAgroApp.empleado.nombre')}
                id="empleado-nombre"
                name="nombre"
                data-cy="nombre"
                type="text"
              />
              <ValidatedField
                label={translate('adminAgroApp.empleado.apellido')}
                id="empleado-apellido"
                name="apellido"
                data-cy="apellido"
                type="text"
              />
              <ValidatedField
                label={translate('adminAgroApp.empleado.email')}
                id="empleado-email"
                name="email"
                data-cy="email"
                type="text"
              />
              <ValidatedField
                label={translate('adminAgroApp.empleado.numeroDeTelefono')}
                id="empleado-numeroDeTelefono"
                name="numeroDeTelefono"
                data-cy="numeroDeTelefono"
                type="text"
              />
              <ValidatedField
                label={translate('adminAgroApp.empleado.salario')}
                id="empleado-salario"
                name="salario"
                data-cy="salario"
                type="text"
              />
              <ValidatedField
                id="empleado-trabajo"
                name="trabajo"
                data-cy="trabajo"
                label={translate('adminAgroApp.empleado.trabajo')}
                type="select"
              >
                <option value="" key="0" />
                {trabajos
                  ? trabajos.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/empleado" replace color="info">
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

export default EmpleadoUpdate;
