import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import ServiceOpt from './service-opt';
import ServiceOptDetail from './service-opt-detail';
import ServiceOptUpdate from './service-opt-update';
import ServiceOptDeleteDialog from './service-opt-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ServiceOptUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ServiceOptUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ServiceOptDetail} />
      <ErrorBoundaryRoute path={match.url} component={ServiceOpt} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={ServiceOptDeleteDialog} />
  </>
);

export default Routes;
