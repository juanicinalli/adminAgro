import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './campo.reducer';

export const CampoDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const campoEntity = useAppSelector(state => state.campo.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="campoDetailsHeading">
          <Translate contentKey="adminAgroApp.campo.detail.title">Campo</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{campoEntity.id}</dd>
          <dt>
            <span id="numeroDeLote">
              <Translate contentKey="adminAgroApp.campo.numeroDeLote">Numero De Lote</Translate>
            </span>
          </dt>
          <dd>{campoEntity.numeroDeLote}</dd>
          <dt>
            <span id="superficie">
              <Translate contentKey="adminAgroApp.campo.superficie">Superficie</Translate>
            </span>
          </dt>
          <dd>{campoEntity.superficie}</dd>
          <dt>
            <span id="tenencia">
              <Translate contentKey="adminAgroApp.campo.tenencia">Tenencia</Translate>
            </span>
          </dt>
          <dd>{campoEntity.tenencia}</dd>
          <dt>
            <span id="cultivo">
              <Translate contentKey="adminAgroApp.campo.cultivo">Cultivo</Translate>
            </span>
          </dt>
          <dd>{campoEntity.cultivo}</dd>
          <dt>
            <Translate contentKey="adminAgroApp.campo.manejo">Manejo</Translate>
          </dt>
          <dd>{campoEntity.manejo ? campoEntity.manejo.id : ''}</dd>
          <dt>
            <Translate contentKey="adminAgroApp.campo.localidad">Localidad</Translate>
          </dt>
          <dd>{campoEntity.localidad ? campoEntity.localidad.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/campo" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/campo/${campoEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default CampoDetail;
