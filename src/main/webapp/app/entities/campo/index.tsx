import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Campo from './campo';
import CampoDetail from './campo-detail';
import CampoUpdate from './campo-update';
import CampoDeleteDialog from './campo-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={CampoUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={CampoUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={CampoDetail} />
      <ErrorBoundaryRoute path={match.url} component={Campo} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={CampoDeleteDialog} />
  </>
);

export default Routes;
