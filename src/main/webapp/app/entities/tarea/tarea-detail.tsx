import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './tarea.reducer';

export const TareaDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const tareaEntity = useAppSelector(state => state.tarea.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="tareaDetailsHeading">
          <Translate contentKey="adminAgroApp.tarea.detail.title">Tarea</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{tareaEntity.id}</dd>
          <dt>
            <span id="nombre">
              <Translate contentKey="adminAgroApp.tarea.nombre">Nombre</Translate>
            </span>
          </dt>
          <dd>{tareaEntity.nombre}</dd>
          <dt>
            <span id="descripcion">
              <Translate contentKey="adminAgroApp.tarea.descripcion">Descripcion</Translate>
            </span>
          </dt>
          <dd>{tareaEntity.descripcion}</dd>
          <dt>
            <Translate contentKey="adminAgroApp.tarea.trabajo">Trabajo</Translate>
          </dt>
          <dd>{tareaEntity.trabajo ? tareaEntity.trabajo.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/tarea" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/tarea/${tareaEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default TareaDetail;
