import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Localidad from './localidad';
import LocalidadDetail from './localidad-detail';
import LocalidadUpdate from './localidad-update';
import LocalidadDeleteDialog from './localidad-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={LocalidadUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={LocalidadUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={LocalidadDetail} />
      <ErrorBoundaryRoute path={match.url} component={Localidad} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={LocalidadDeleteDialog} />
  </>
);

export default Routes;
