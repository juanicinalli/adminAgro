import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './trabajo.reducer';

export const TrabajoDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const trabajoEntity = useAppSelector(state => state.trabajo.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="trabajoDetailsHeading">
          <Translate contentKey="adminAgroApp.trabajo.detail.title">Trabajo</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{trabajoEntity.id}</dd>
          <dt>
            <span id="puesto">
              <Translate contentKey="adminAgroApp.trabajo.puesto">Puesto</Translate>
            </span>
          </dt>
          <dd>{trabajoEntity.puesto}</dd>
          <dt>
            <span id="salario">
              <Translate contentKey="adminAgroApp.trabajo.salario">Salario</Translate>
            </span>
          </dt>
          <dd>{trabajoEntity.salario}</dd>
        </dl>
        <Button tag={Link} to="/trabajo" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/trabajo/${trabajoEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default TrabajoDetail;
