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
import { ITarea } from 'app/shared/model/tarea.model';
import { getEntity, updateEntity, createEntity, reset } from './tarea.reducer';

export const TareaUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const trabajos = useAppSelector(state => state.trabajo.entities);
  const tareaEntity = useAppSelector(state => state.tarea.entity);
  const loading = useAppSelector(state => state.tarea.loading);
  const updating = useAppSelector(state => state.tarea.updating);
  const updateSuccess = useAppSelector(state => state.tarea.updateSuccess);
  const handleClose = () => {
    props.history.push('/tarea' + props.location.search);
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
      ...tareaEntity,
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
          ...tareaEntity,
          trabajo: tareaEntity?.trabajo?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="adminAgroApp.tarea.home.createOrEditLabel" data-cy="TareaCreateUpdateHeading">
            <Translate contentKey="adminAgroApp.tarea.home.createOrEditLabel">Create or edit a Tarea</Translate>
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
                  id="tarea-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField label={translate('adminAgroApp.tarea.nombre')} id="tarea-nombre" name="nombre" data-cy="nombre" type="text" />
              <ValidatedField
                label={translate('adminAgroApp.tarea.descripcion')}
                id="tarea-descripcion"
                name="descripcion"
                data-cy="descripcion"
                type="text"
              />
              <ValidatedField
                id="tarea-trabajo"
                name="trabajo"
                data-cy="trabajo"
                label={translate('adminAgroApp.tarea.trabajo')}
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/tarea" replace color="info">
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

export default TareaUpdate;
