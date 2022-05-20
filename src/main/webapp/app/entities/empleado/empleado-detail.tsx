import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './empleado.reducer';

export const EmpleadoDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const empleadoEntity = useAppSelector(state => state.empleado.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="empleadoDetailsHeading">
          <Translate contentKey="adminAgroApp.empleado.detail.title">Empleado</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{empleadoEntity.id}</dd>
          <dt>
            <span id="nombre">
              <Translate contentKey="adminAgroApp.empleado.nombre">Nombre</Translate>
            </span>
          </dt>
          <dd>{empleadoEntity.nombre}</dd>
          <dt>
            <span id="apellido">
              <Translate contentKey="adminAgroApp.empleado.apellido">Apellido</Translate>
            </span>
          </dt>
          <dd>{empleadoEntity.apellido}</dd>
          <dt>
            <span id="email">
              <Translate contentKey="adminAgroApp.empleado.email">Email</Translate>
            </span>
          </dt>
          <dd>{empleadoEntity.email}</dd>
          <dt>
            <span id="numeroDeTelefono">
              <Translate contentKey="adminAgroApp.empleado.numeroDeTelefono">Numero De Telefono</Translate>
            </span>
          </dt>
          <dd>{empleadoEntity.numeroDeTelefono}</dd>
          <dt>
            <span id="salario">
              <Translate contentKey="adminAgroApp.empleado.salario">Salario</Translate>
            </span>
          </dt>
          <dd>{empleadoEntity.salario}</dd>
          <dt>
            <Translate contentKey="adminAgroApp.empleado.trabajo">Trabajo</Translate>
          </dt>
          <dd>{empleadoEntity.trabajo ? empleadoEntity.trabajo.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/empleado" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/empleado/${empleadoEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default EmpleadoDetail;
