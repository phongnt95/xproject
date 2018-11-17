import React from 'react';
import { Switch } from 'react-router-dom';

// tslint:disable-next-line:no-unused-variable
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Product from './product';
import Image from './image';
import Location from './location';
import ProductInfo from './product-info';
import ServiceOpt from './service-opt';
import Code from './code';
import BehaviorRecording from './behavior-recording';
import Comment from './comment';
import UserX from './user-x';
/* jhipster-needle-add-route-import - JHipster will add routes here */

const Routes = ({ match }) => (
  <div>
    <Switch>
      {/* prettier-ignore */}
      <ErrorBoundaryRoute path={`${match.url}/product`} component={Product} />
      <ErrorBoundaryRoute path={`${match.url}/image`} component={Image} />
      <ErrorBoundaryRoute path={`${match.url}/location`} component={Location} />
      <ErrorBoundaryRoute path={`${match.url}/product-info`} component={ProductInfo} />
      <ErrorBoundaryRoute path={`${match.url}/service-opt`} component={ServiceOpt} />
      <ErrorBoundaryRoute path={`${match.url}/code`} component={Code} />
      <ErrorBoundaryRoute path={`${match.url}/behavior-recording`} component={BehaviorRecording} />
      <ErrorBoundaryRoute path={`${match.url}/comment`} component={Comment} />
      <ErrorBoundaryRoute path={`${match.url}/user-x`} component={UserX} />
      {/* jhipster-needle-add-route-path - JHipster will routes here */}
    </Switch>
  </div>
);

export default Routes;
