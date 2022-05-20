import React from 'react';
import { Switch } from 'react-router-dom';
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Pais from './pais';
import Provincia from './provincia';
import Localidad from './localidad';
import Empleado from './empleado';
import Trabajo from './trabajo';
import Tarea from './tarea';
import Campo from './campo';
import Insumos from './insumos';
import Manejo from './manejo';
/* jhipster-needle-add-route-import - JHipster will add routes here */

export default ({ match }) => {
  return (
    <div>
      <Switch>
        {/* prettier-ignore */}
        <ErrorBoundaryRoute path={`${match.url}pais`} component={Pais} />
        <ErrorBoundaryRoute path={`${match.url}provincia`} component={Provincia} />
        <ErrorBoundaryRoute path={`${match.url}localidad`} component={Localidad} />
        <ErrorBoundaryRoute path={`${match.url}empleado`} component={Empleado} />
        <ErrorBoundaryRoute path={`${match.url}trabajo`} component={Trabajo} />
        <ErrorBoundaryRoute path={`${match.url}tarea`} component={Tarea} />
        <ErrorBoundaryRoute path={`${match.url}campo`} component={Campo} />
        <ErrorBoundaryRoute path={`${match.url}insumos`} component={Insumos} />
        <ErrorBoundaryRoute path={`${match.url}manejo`} component={Manejo} />
        {/* jhipster-needle-add-route-path - JHipster will add routes here */}
      </Switch>
    </div>
  );
};
