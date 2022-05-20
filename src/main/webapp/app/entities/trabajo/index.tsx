import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Trabajo from './trabajo';
import TrabajoDetail from './trabajo-detail';
import TrabajoUpdate from './trabajo-update';
import TrabajoDeleteDialog from './trabajo-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={TrabajoUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={TrabajoUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={TrabajoDetail} />
      <ErrorBoundaryRoute path={match.url} component={Trabajo} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={TrabajoDeleteDialog} />
  </>
);

export default Routes;
