import React from 'react';
import { Switch } from 'react-router-dom';

// tslint:disable-next-line:no-unused-variable
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Page from './page';
import Group from './group';
/* jhipster-needle-add-route-import - JHipster will add routes here */

const Routes = ({ match }) => (
  <div>
    <Switch>
      {/* prettier-ignore */}
      <ErrorBoundaryRoute path={`${match.url}/page`} component={Page} />
      <ErrorBoundaryRoute path={`${match.url}/group`} component={Group} />
      {/* jhipster-needle-add-route-path - JHipster will add routes here */}
    </Switch>
  </div>
);

export default Routes;
