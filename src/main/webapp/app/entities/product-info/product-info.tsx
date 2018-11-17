import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAllAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './product-info.reducer';
import { IProductInfo } from 'app/shared/model/product-info.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IProductInfoProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export class ProductInfo extends React.Component<IProductInfoProps> {
  componentDidMount() {
    this.props.getEntities();
  }

  render() {
    const { productInfoList, match } = this.props;
    return (
      <div>
        <h2 id="product-info-heading">
          <Translate contentKey="xprojectApp.productInfo.home.title">Product Infos</Translate>
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="xprojectApp.productInfo.home.createLabel">Create new Product Info</Translate>
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
                  <Translate contentKey="xprojectApp.productInfo.weight">Weight</Translate>
                </th>
                <th>
                  <Translate contentKey="xprojectApp.productInfo.high">High</Translate>
                </th>
                <th>
                  <Translate contentKey="xprojectApp.productInfo.measurement1">Measurement 1</Translate>
                </th>
                <th>
                  <Translate contentKey="xprojectApp.productInfo.measurement2">Measurement 2</Translate>
                </th>
                <th>
                  <Translate contentKey="xprojectApp.productInfo.measurement3">Measurement 3</Translate>
                </th>
                <th>
                  <Translate contentKey="xprojectApp.productInfo.description">Description</Translate>
                </th>
                <th>
                  <Translate contentKey="xprojectApp.productInfo.phoneNumber">Phone Number</Translate>
                </th>
                <th>
                  <Translate contentKey="xprojectApp.productInfo.joinTime">Join Time</Translate>
                </th>
                <th>
                  <Translate contentKey="xprojectApp.productInfo.comeFrom">Come From</Translate>
                </th>
                <th>
                  <Translate contentKey="xprojectApp.productInfo.xInfo">X Info</Translate>
                </th>
                <th>
                  <Translate contentKey="xprojectApp.productInfo.productInfo">Product Info</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {productInfoList.map((productInfo, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${productInfo.id}`} color="link" size="sm">
                      {productInfo.id}
                    </Button>
                  </td>
                  <td>{productInfo.weight}</td>
                  <td>{productInfo.high}</td>
                  <td>{productInfo.measurement1}</td>
                  <td>{productInfo.measurement2}</td>
                  <td>{productInfo.measurement3}</td>
                  <td>{productInfo.description}</td>
                  <td>{productInfo.phoneNumber}</td>
                  <td>
                    <TextFormat type="date" value={productInfo.joinTime} format={APP_DATE_FORMAT} />
                  </td>
                  <td>{productInfo.comeFrom}</td>
                  <td>{productInfo.xInfo}</td>
                  <td>
                    {productInfo.productInfo ? <Link to={`product/${productInfo.productInfo.id}`}>{productInfo.productInfo.id}</Link> : ''}
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${productInfo.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${productInfo.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${productInfo.id}/delete`} color="danger" size="sm">
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

const mapStateToProps = ({ productInfo }: IRootState) => ({
  productInfoList: productInfo.entities
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(ProductInfo);
