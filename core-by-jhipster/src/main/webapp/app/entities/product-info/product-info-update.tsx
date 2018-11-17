import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IProduct } from 'app/shared/model/product.model';
import { getEntities as getProducts } from 'app/entities/product/product.reducer';
import { getEntity, updateEntity, createEntity, reset } from './product-info.reducer';
import { IProductInfo } from 'app/shared/model/product-info.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IProductInfoUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IProductInfoUpdateState {
  isNew: boolean;
  productInfoId: string;
}

export class ProductInfoUpdate extends React.Component<IProductInfoUpdateProps, IProductInfoUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      productInfoId: '0',
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

    this.props.getProducts();
  }

  saveEntity = (event, errors, values) => {
    values.joinTime = new Date(values.joinTime);

    if (errors.length === 0) {
      const { productInfoEntity } = this.props;
      const entity = {
        ...productInfoEntity,
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
    this.props.history.push('/entity/product-info');
  };

  render() {
    const { productInfoEntity, products, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="xprojectApp.productInfo.home.createOrEditLabel">
              <Translate contentKey="xprojectApp.productInfo.home.createOrEditLabel">Create or edit a ProductInfo</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : productInfoEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="product-info-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="weightLabel" for="weight">
                    <Translate contentKey="xprojectApp.productInfo.weight">Weight</Translate>
                  </Label>
                  <AvField id="product-info-weight" type="string" className="form-control" name="weight" />
                </AvGroup>
                <AvGroup>
                  <Label id="highLabel" for="high">
                    <Translate contentKey="xprojectApp.productInfo.high">High</Translate>
                  </Label>
                  <AvField id="product-info-high" type="string" className="form-control" name="high" />
                </AvGroup>
                <AvGroup>
                  <Label id="measurement1Label" for="measurement1">
                    <Translate contentKey="xprojectApp.productInfo.measurement1">Measurement 1</Translate>
                  </Label>
                  <AvField id="product-info-measurement1" type="text" name="measurement1" />
                </AvGroup>
                <AvGroup>
                  <Label id="measurement2Label" for="measurement2">
                    <Translate contentKey="xprojectApp.productInfo.measurement2">Measurement 2</Translate>
                  </Label>
                  <AvField id="product-info-measurement2" type="text" name="measurement2" />
                </AvGroup>
                <AvGroup>
                  <Label id="measurement3Label" for="measurement3">
                    <Translate contentKey="xprojectApp.productInfo.measurement3">Measurement 3</Translate>
                  </Label>
                  <AvField id="product-info-measurement3" type="text" name="measurement3" />
                </AvGroup>
                <AvGroup>
                  <Label id="descriptionLabel" for="description">
                    <Translate contentKey="xprojectApp.productInfo.description">Description</Translate>
                  </Label>
                  <AvField
                    id="product-info-description"
                    type="text"
                    name="description"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="phoneNumberLabel" for="phoneNumber">
                    <Translate contentKey="xprojectApp.productInfo.phoneNumber">Phone Number</Translate>
                  </Label>
                  <AvField
                    id="product-info-phoneNumber"
                    type="text"
                    name="phoneNumber"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="joinTimeLabel" for="joinTime">
                    <Translate contentKey="xprojectApp.productInfo.joinTime">Join Time</Translate>
                  </Label>
                  <AvInput
                    id="product-info-joinTime"
                    type="datetime-local"
                    className="form-control"
                    name="joinTime"
                    value={isNew ? null : convertDateTimeFromServer(this.props.productInfoEntity.joinTime)}
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="comeFromLabel" for="comeFrom">
                    <Translate contentKey="xprojectApp.productInfo.comeFrom">Come From</Translate>
                  </Label>
                  <AvField
                    id="product-info-comeFrom"
                    type="text"
                    name="comeFrom"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="xInfoLabel" for="xInfo">
                    <Translate contentKey="xprojectApp.productInfo.xInfo">X Info</Translate>
                  </Label>
                  <AvField
                    id="product-info-xInfo"
                    type="text"
                    name="xInfo"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label for="productInfo.id">
                    <Translate contentKey="xprojectApp.productInfo.productInfo">Product Info</Translate>
                  </Label>
                  <AvInput id="product-info-productInfo" type="select" className="form-control" name="productInfo.id">
                    <option value="" key="0" />
                    {products
                      ? products.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/product-info" replace color="info">
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
  products: storeState.product.entities,
  productInfoEntity: storeState.productInfo.entity,
  loading: storeState.productInfo.loading,
  updating: storeState.productInfo.updating,
  updateSuccess: storeState.productInfo.updateSuccess
});

const mapDispatchToProps = {
  getProducts,
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
)(ProductInfoUpdate);
