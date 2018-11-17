import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './user-x.reducer';
import { IUserX } from 'app/shared/model/user-x.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IUserXDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class UserXDetail extends React.Component<IUserXDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { userXEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="xprojectApp.userX.detail.title">UserX</Translate> [<b>{userXEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="username">
                <Translate contentKey="xprojectApp.userX.username">Username</Translate>
              </span>
            </dt>
            <dd>{userXEntity.username}</dd>
            <dt>
              <span id="password">
                <Translate contentKey="xprojectApp.userX.password">Password</Translate>
              </span>
            </dt>
            <dd>{userXEntity.password}</dd>
            <dt>
              <span id="fullname">
                <Translate contentKey="xprojectApp.userX.fullname">Fullname</Translate>
              </span>
            </dt>
            <dd>{userXEntity.fullname}</dd>
            <dt>
              <span id="phoneNumber">
                <Translate contentKey="xprojectApp.userX.phoneNumber">Phone Number</Translate>
              </span>
            </dt>
            <dd>{userXEntity.phoneNumber}</dd>
            <dt>
              <span id="xInfo">
                <Translate contentKey="xprojectApp.userX.xInfo">X Info</Translate>
              </span>
            </dt>
            <dd>{userXEntity.xInfo}</dd>
            <dt>
              <span id="status">
                <Translate contentKey="xprojectApp.userX.status">Status</Translate>
              </span>
            </dt>
            <dd>{userXEntity.status ? 'true' : 'false'}</dd>
            <dt>
              <span id="datetime">
                <Translate contentKey="xprojectApp.userX.datetime">Datetime</Translate>
              </span>
            </dt>
            <dd>
              <TextFormat value={userXEntity.datetime} type="date" format={APP_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="role">
                <Translate contentKey="xprojectApp.userX.role">Role</Translate>
              </span>
            </dt>
            <dd>{userXEntity.role}</dd>
          </dl>
          <Button tag={Link} to="/entity/user-x" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/user-x/${userXEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.edit">Edit</Translate>
            </span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ userX }: IRootState) => ({
  userXEntity: userX.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(UserXDetail);
