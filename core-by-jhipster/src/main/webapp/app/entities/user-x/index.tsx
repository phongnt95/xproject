import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import UserX from './user-x';
import UserXDetail from './user-x-detail';
import UserXUpdate from './user-x-update';
import UserXDeleteDialog from './user-x-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={UserXUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={UserXUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={UserXDetail} />
      <ErrorBoundaryRoute path={match.url} component={UserX} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={UserXDeleteDialog} />
  </>
);

export default Routes;
