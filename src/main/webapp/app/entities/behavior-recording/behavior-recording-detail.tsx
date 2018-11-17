import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './behavior-recording.reducer';
import { IBehaviorRecording } from 'app/shared/model/behavior-recording.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IBehaviorRecordingDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class BehaviorRecordingDetail extends React.Component<IBehaviorRecordingDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { behaviorRecordingEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="xprojectApp.behaviorRecording.detail.title">BehaviorRecording</Translate> [
            <b>{behaviorRecordingEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="action">
                <Translate contentKey="xprojectApp.behaviorRecording.action">Action</Translate>
              </span>
            </dt>
            <dd>{behaviorRecordingEntity.action}</dd>
            <dt>
              <span id="datetime">
                <Translate contentKey="xprojectApp.behaviorRecording.datetime">Datetime</Translate>
              </span>
            </dt>
            <dd>
              <TextFormat value={behaviorRecordingEntity.datetime} type="date" format={APP_DATE_FORMAT} />
            </dd>
          </dl>
          <Button tag={Link} to="/entity/behavior-recording" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/behavior-recording/${behaviorRecordingEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ behaviorRecording }: IRootState) => ({
  behaviorRecordingEntity: behaviorRecording.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(BehaviorRecordingDetail);
