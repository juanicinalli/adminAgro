import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Manejo from './manejo';
import ManejoDetail from './manejo-detail';
import ManejoUpdate from './manejo-update';
import ManejoDeleteDialog from './manejo-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ManejoUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ManejoUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ManejoDetail} />
      <ErrorBoundaryRoute path={match.url} component={Manejo} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={ManejoDeleteDialog} />
  </>
);

export default Routes;
