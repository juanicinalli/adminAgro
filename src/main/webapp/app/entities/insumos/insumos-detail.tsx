import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './insumos.reducer';

export const InsumosDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const insumosEntity = useAppSelector(state => state.insumos.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="insumosDetailsHeading">
          <Translate contentKey="adminAgroApp.insumos.detail.title">Insumos</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{insumosEntity.id}</dd>
          <dt>
            <span id="categoria">
              <Translate contentKey="adminAgroApp.insumos.categoria">Categoria</Translate>
            </span>
          </dt>
          <dd>{insumosEntity.categoria}</dd>
          <dt>
            <span id="nombre">
              <Translate contentKey="adminAgroApp.insumos.nombre">Nombre</Translate>
            </span>
          </dt>
          <dd>{insumosEntity.nombre}</dd>
          <dt>
            <span id="precioPorUnidad">
              <Translate contentKey="adminAgroApp.insumos.precioPorUnidad">Precio Por Unidad</Translate>
            </span>
          </dt>
          <dd>{insumosEntity.precioPorUnidad}</dd>
          <dt>
            <span id="unidad">
              <Translate contentKey="adminAgroApp.insumos.unidad">Unidad</Translate>
            </span>
          </dt>
          <dd>{insumosEntity.unidad}</dd>
          <dt>
            <Translate contentKey="adminAgroApp.insumos.manejo">Manejo</Translate>
          </dt>
          <dd>{insumosEntity.manejo ? insumosEntity.manejo.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/insumos" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/insumos/${insumosEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default InsumosDetail;
