import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './service-opt.reducer';
import { IServiceOpt } from 'app/shared/model/service-opt.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IServiceOptDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class ServiceOptDetail extends React.Component<IServiceOptDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { serviceOptEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="xprojectApp.serviceOpt.detail.title">ServiceOpt</Translate> [<b>{serviceOptEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="name">
                <Translate contentKey="xprojectApp.serviceOpt.name">Name</Translate>
              </span>
            </dt>
            <dd>{serviceOptEntity.name}</dd>
            <dt>
              <Translate contentKey="xprojectApp.serviceOpt.opts">Opts</Translate>
            </dt>
            <dd>
              {serviceOptEntity.opts
                ? serviceOptEntity.opts.map((val, i) => (
                    <span key={val.id}>
                      <a>{val.id}</a>
                      {i === serviceOptEntity.opts.length - 1 ? '' : ', '}
                    </span>
                  ))
                : null}
            </dd>
          </dl>
          <Button tag={Link} to="/entity/service-opt" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/service-opt/${serviceOptEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ serviceOpt }: IRootState) => ({
  serviceOptEntity: serviceOpt.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(ServiceOptDetail);
