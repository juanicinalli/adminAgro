import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './localidad.reducer';

export const LocalidadDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const localidadEntity = useAppSelector(state => state.localidad.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="localidadDetailsHeading">
          <Translate contentKey="adminAgroApp.localidad.detail.title">Localidad</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{localidadEntity.id}</dd>
          <dt>
            <span id="nombre">
              <Translate contentKey="adminAgroApp.localidad.nombre">Nombre</Translate>
            </span>
          </dt>
          <dd>{localidadEntity.nombre}</dd>
          <dt>
            <span id="direccion">
              <Translate contentKey="adminAgroApp.localidad.direccion">Direccion</Translate>
            </span>
          </dt>
          <dd>{localidadEntity.direccion}</dd>
          <dt>
            <span id="codigoPostal">
              <Translate contentKey="adminAgroApp.localidad.codigoPostal">Codigo Postal</Translate>
            </span>
          </dt>
          <dd>{localidadEntity.codigoPostal}</dd>
          <dt>
            <Translate contentKey="adminAgroApp.localidad.provincia">Provincia</Translate>
          </dt>
          <dd>{localidadEntity.provincia ? localidadEntity.provincia.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/localidad" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/localidad/${localidadEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default LocalidadDetail;
