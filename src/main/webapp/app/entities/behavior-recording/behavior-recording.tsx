import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAllAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './behavior-recording.reducer';
import { IBehaviorRecording } from 'app/shared/model/behavior-recording.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IBehaviorRecordingProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export class BehaviorRecording extends React.Component<IBehaviorRecordingProps> {
  componentDidMount() {
    this.props.getEntities();
  }

  render() {
    const { behaviorRecordingList, match } = this.props;
    return (
      <div>
        <h2 id="behavior-recording-heading">
          <Translate contentKey="xprojectApp.behaviorRecording.home.title">Behavior Recordings</Translate>
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="xprojectApp.behaviorRecording.home.createLabel">Create new Behavior Recording</Translate>
          </Link>
        </h2>
        <div className="table-responsive">
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="global.field.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="xprojectApp.behaviorRecording.action">Action</Translate>
                </th>
                <th>
                  <Translate contentKey="xprojectApp.behaviorRecording.datetime">Datetime</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {behaviorRecordingList.map((behaviorRecording, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${behaviorRecording.id}`} color="link" size="sm">
                      {behaviorRecording.id}
                    </Button>
                  </td>
                  <td>
                    <Translate contentKey={`xprojectApp.Action.${behaviorRecording.action}`} />
                  </td>
                  <td>
                    <TextFormat type="date" value={behaviorRecording.datetime} format={APP_DATE_FORMAT} />
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${behaviorRecording.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${behaviorRecording.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${behaviorRecording.id}/delete`} color="danger" size="sm">
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        </div>
      </div>
    );
  }
}

const mapStateToProps = ({ behaviorRecording }: IRootState) => ({
  behaviorRecordingList: behaviorRecording.entities
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(BehaviorRecording);
