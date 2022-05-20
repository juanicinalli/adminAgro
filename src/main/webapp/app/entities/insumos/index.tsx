import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Insumos from './insumos';
import InsumosDetail from './insumos-detail';
import InsumosUpdate from './insumos-update';
import InsumosDeleteDialog from './insumos-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={InsumosUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={InsumosUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={InsumosDetail} />
      <ErrorBoundaryRoute path={match.url} component={Insumos} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={InsumosDeleteDialog} />
  </>
);

export default Routes;
