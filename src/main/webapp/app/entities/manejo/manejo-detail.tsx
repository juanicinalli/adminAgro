import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './manejo.reducer';

export const ManejoDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const manejoEntity = useAppSelector(state => state.manejo.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="manejoDetailsHeading">
          <Translate contentKey="adminAgroApp.manejo.detail.title">Manejo</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{manejoEntity.id}</dd>
          <dt>
            <span id="labor">
              <Translate contentKey="adminAgroApp.manejo.labor">Labor</Translate>
            </span>
          </dt>
          <dd>{manejoEntity.labor}</dd>
          <dt>
            <span id="mes">
              <Translate contentKey="adminAgroApp.manejo.mes">Mes</Translate>
            </span>
          </dt>
          <dd>{manejoEntity.mes}</dd>
          <dt>
            <span id="costo">
              <Translate contentKey="adminAgroApp.manejo.costo">Costo</Translate>
            </span>
          </dt>
          <dd>{manejoEntity.costo}</dd>
        </dl>
        <Button tag={Link} to="/manejo" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/manejo/${manejoEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ManejoDetail;
