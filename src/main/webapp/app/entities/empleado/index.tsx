import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Empleado from './empleado';
import EmpleadoDetail from './empleado-detail';
import EmpleadoUpdate from './empleado-update';
import EmpleadoDeleteDialog from './empleado-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={EmpleadoUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={EmpleadoUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={EmpleadoDetail} />
      <ErrorBoundaryRoute path={match.url} component={Empleado} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={EmpleadoDeleteDialog} />
  </>
);

export default Routes;
