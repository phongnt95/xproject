import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './user-x.reducer';
import { IUserX } from 'app/shared/model/user-x.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IUserXUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IUserXUpdateState {
  isNew: boolean;
}

export class UserXUpdate extends React.Component<IUserXUpdateProps, IUserXUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      isNew: !this.props.match.params || !this.props.match.params.id
    };
  }

  componentWillUpdate(nextProps, nextState) {
    if (nextProps.updateSuccess !== this.props.updateSuccess && nextProps.updateSuccess) {
      this.handleClose();
    }
  }

  componentDidMount() {
    if (this.state.isNew) {
      this.props.reset();
    } else {
      this.props.getEntity(this.props.match.params.id);
    }
  }

  saveEntity = (event, errors, values) => {
    values.datetime = new Date(values.datetime);

    if (errors.length === 0) {
      const { userXEntity } = this.props;
      const entity = {
        ...userXEntity,
        ...values
      };

      if (this.state.isNew) {
        this.props.createEntity(entity);
      } else {
        this.props.updateEntity(entity);
      }
    }
  };

  handleClose = () => {
    this.props.history.push('/entity/user-x');
  };

  render() {
    const { userXEntity, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="xprojectApp.userX.home.createOrEditLabel">
              <Translate contentKey="xprojectApp.userX.home.createOrEditLabel">Create or edit a UserX</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : userXEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="user-x-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="usernameLabel" for="username">
                    <Translate contentKey="xprojectApp.userX.username">Username</Translate>
                  </Label>
                  <AvField
                    id="user-x-username"
                    type="text"
                    name="username"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="passwordLabel" for="password">
                    <Translate contentKey="xprojectApp.userX.password">Password</Translate>
                  </Label>
                  <AvField
                    id="user-x-password"
                    type="text"
                    name="password"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="fullnameLabel" for="fullname">
                    <Translate contentKey="xprojectApp.userX.fullname">Fullname</Translate>
                  </Label>
                  <AvField
                    id="user-x-fullname"
                    type="text"
                    name="fullname"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="phoneNumberLabel" for="phoneNumber">
                    <Translate contentKey="xprojectApp.userX.phoneNumber">Phone Number</Translate>
                  </Label>
                  <AvField
                    id="user-x-phoneNumber"
                    type="text"
                    name="phoneNumber"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="xInfoLabel" for="xInfo">
                    <Translate contentKey="xprojectApp.userX.xInfo">X Info</Translate>
                  </Label>
                  <AvField
                    id="user-x-xInfo"
                    type="text"
                    name="xInfo"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="statusLabel" check>
                    <AvInput id="user-x-status" type="checkbox" className="form-control" name="status" />
                    <Translate contentKey="xprojectApp.userX.status">Status</Translate>
                  </Label>
                </AvGroup>
                <AvGroup>
                  <Label id="datetimeLabel" for="datetime">
                    <Translate contentKey="xprojectApp.userX.datetime">Datetime</Translate>
                  </Label>
                  <AvInput
                    id="user-x-datetime"
                    type="datetime-local"
                    className="form-control"
                    name="datetime"
                    value={isNew ? null : convertDateTimeFromServer(this.props.userXEntity.datetime)}
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="roleLabel">
                    <Translate contentKey="xprojectApp.userX.role">Role</Translate>
                  </Label>
                  <AvInput
                    id="user-x-role"
                    type="select"
                    className="form-control"
                    name="role"
                    value={(!isNew && userXEntity.role) || 'CHECKER'}
                  >
                    <option value="CHECKER">
                      <Translate contentKey="xprojectApp.XRole.CHECKER" />
                    </option>
                    <option value="BROKER">
                      <Translate contentKey="xprojectApp.XRole.BROKER" />
                    </option>
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/user-x" replace color="info">
                  <FontAwesomeIcon icon="arrow-left" />
                  &nbsp;
                  <span className="d-none d-md-inline">
                    <Translate contentKey="entity.action.back">Back</Translate>
                  </span>
                </Button>
                &nbsp;
                <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                  <FontAwesomeIcon icon="save" />
                  &nbsp;
                  <Translate contentKey="entity.action.save">Save</Translate>
                </Button>
              </AvForm>
            )}
          </Col>
        </Row>
      </div>
    );
  }
}

const mapStateToProps = (storeState: IRootState) => ({
  userXEntity: storeState.userX.entity,
  loading: storeState.userX.loading,
  updating: storeState.userX.updating,
  updateSuccess: storeState.userX.updateSuccess
});

const mapDispatchToProps = {
  getEntity,
  updateEntity,
  createEntity,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(UserXUpdate);
