import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IManejo } from 'app/shared/model/manejo.model';
import { getEntity, updateEntity, createEntity, reset } from './manejo.reducer';

export const ManejoUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const manejoEntity = useAppSelector(state => state.manejo.entity);
  const loading = useAppSelector(state => state.manejo.loading);
  const updating = useAppSelector(state => state.manejo.updating);
  const updateSuccess = useAppSelector(state => state.manejo.updateSuccess);
  const handleClose = () => {
    props.history.push('/manejo');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(props.match.params.id));
    }
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...manejoEntity,
      ...values,
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
          ...manejoEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="adminAgroApp.manejo.home.createOrEditLabel" data-cy="ManejoCreateUpdateHeading">
            <Translate contentKey="adminAgroApp.manejo.home.createOrEditLabel">Create or edit a Manejo</Translate>
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
                  id="manejo-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField label={translate('adminAgroApp.manejo.labor')} id="manejo-labor" name="labor" data-cy="labor" type="text" />
              <ValidatedField label={translate('adminAgroApp.manejo.mes')} id="manejo-mes" name="mes" data-cy="mes" type="text" />
              <ValidatedField label={translate('adminAgroApp.manejo.costo')} id="manejo-costo" name="costo" data-cy="costo" type="text" />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/manejo" replace color="info">
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

export default ManejoUpdate;
