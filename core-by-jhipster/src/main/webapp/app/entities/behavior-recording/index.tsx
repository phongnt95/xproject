import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import BehaviorRecording from './behavior-recording';
import BehaviorRecordingDetail from './behavior-recording-detail';
import BehaviorRecordingUpdate from './behavior-recording-update';
import BehaviorRecordingDeleteDialog from './behavior-recording-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={BehaviorRecordingUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={BehaviorRecordingUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={BehaviorRecordingDetail} />
      <ErrorBoundaryRoute path={match.url} component={BehaviorRecording} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={BehaviorRecordingDeleteDialog} />
  </>
);

export default Routes;
