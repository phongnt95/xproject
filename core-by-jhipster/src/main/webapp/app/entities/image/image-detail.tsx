import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './image.reducer';
import { IImage } from 'app/shared/model/image.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IImageDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class ImageDetail extends React.Component<IImageDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { imageEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="xprojectApp.image.detail.title">Image</Translate> [<b>{imageEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="url">
                <Translate contentKey="xprojectApp.image.url">Url</Translate>
              </span>
            </dt>
            <dd>{imageEntity.url}</dd>
            <dt>
              <span id="isWallpaper">
                <Translate contentKey="xprojectApp.image.isWallpaper">Is Wallpaper</Translate>
              </span>
            </dt>
            <dd>{imageEntity.isWallpaper ? 'true' : 'false'}</dd>
            <dt>
              <Translate contentKey="xprojectApp.image.images">Images</Translate>
            </dt>
            <dd>{imageEntity.images ? imageEntity.images.id : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/image" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/image/${imageEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ image }: IRootState) => ({
  imageEntity: image.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(ImageDetail);
