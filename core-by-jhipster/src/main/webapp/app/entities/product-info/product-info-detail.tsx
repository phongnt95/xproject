import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './product-info.reducer';
import { IProductInfo } from 'app/shared/model/product-info.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IProductInfoDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class ProductInfoDetail extends React.Component<IProductInfoDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { productInfoEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="xprojectApp.productInfo.detail.title">ProductInfo</Translate> [<b>{productInfoEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="weight">
                <Translate contentKey="xprojectApp.productInfo.weight">Weight</Translate>
              </span>
            </dt>
            <dd>{productInfoEntity.weight}</dd>
            <dt>
              <span id="high">
                <Translate contentKey="xprojectApp.productInfo.high">High</Translate>
              </span>
            </dt>
            <dd>{productInfoEntity.high}</dd>
            <dt>
              <span id="measurement1">
                <Translate contentKey="xprojectApp.productInfo.measurement1">Measurement 1</Translate>
              </span>
            </dt>
            <dd>{productInfoEntity.measurement1}</dd>
            <dt>
              <span id="measurement2">
                <Translate contentKey="xprojectApp.productInfo.measurement2">Measurement 2</Translate>
              </span>
            </dt>
            <dd>{productInfoEntity.measurement2}</dd>
            <dt>
              <span id="measurement3">
                <Translate contentKey="xprojectApp.productInfo.measurement3">Measurement 3</Translate>
              </span>
            </dt>
            <dd>{productInfoEntity.measurement3}</dd>
            <dt>
              <span id="description">
                <Translate contentKey="xprojectApp.productInfo.description">Description</Translate>
              </span>
            </dt>
            <dd>{productInfoEntity.description}</dd>
            <dt>
              <span id="phoneNumber">
                <Translate contentKey="xprojectApp.productInfo.phoneNumber">Phone Number</Translate>
              </span>
            </dt>
            <dd>{productInfoEntity.phoneNumber}</dd>
            <dt>
              <span id="joinTime">
                <Translate contentKey="xprojectApp.productInfo.joinTime">Join Time</Translate>
              </span>
            </dt>
            <dd>
              <TextFormat value={productInfoEntity.joinTime} type="date" format={APP_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="comeFrom">
                <Translate contentKey="xprojectApp.productInfo.comeFrom">Come From</Translate>
              </span>
            </dt>
            <dd>{productInfoEntity.comeFrom}</dd>
            <dt>
              <span id="xInfo">
                <Translate contentKey="xprojectApp.productInfo.xInfo">X Info</Translate>
              </span>
            </dt>
            <dd>{productInfoEntity.xInfo}</dd>
            <dt>
              <Translate contentKey="xprojectApp.productInfo.productInfo">Product Info</Translate>
            </dt>
            <dd>{productInfoEntity.productInfo ? productInfoEntity.productInfo.id : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/product-info" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/product-info/${productInfoEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ productInfo }: IRootState) => ({
  productInfoEntity: productInfo.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(ProductInfoDetail);
