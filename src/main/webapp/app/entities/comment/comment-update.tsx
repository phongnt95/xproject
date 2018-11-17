import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntities as getComments } from 'app/entities/comment/comment.reducer';
import { IProduct } from 'app/shared/model/product.model';
import { getEntities as getProducts } from 'app/entities/product/product.reducer';
import { getEntity, updateEntity, createEntity, reset } from './comment.reducer';
import { IComment } from 'app/shared/model/comment.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface ICommentUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface ICommentUpdateState {
  isNew: boolean;
  subId: string;
  commentsId: string;
}

export class CommentUpdate extends React.Component<ICommentUpdateProps, ICommentUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      subId: '0',
      commentsId: '0',
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

    this.props.getComments();
    this.props.getProducts();
  }

  saveEntity = (event, errors, values) => {
    values.datetime = new Date(values.datetime);

    if (errors.length === 0) {
      const { commentEntity } = this.props;
      const entity = {
        ...commentEntity,
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
    this.props.history.push('/entity/comment');
  };

  render() {
    const { commentEntity, comments, products, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="xprojectApp.comment.home.createOrEditLabel">
              <Translate contentKey="xprojectApp.comment.home.createOrEditLabel">Create or edit a Comment</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : commentEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="comment-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="contentLabel" for="content">
                    <Translate contentKey="xprojectApp.comment.content">Content</Translate>
                  </Label>
                  <AvField
                    id="comment-content"
                    type="text"
                    name="content"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="datetimeLabel" for="datetime">
                    <Translate contentKey="xprojectApp.comment.datetime">Datetime</Translate>
                  </Label>
                  <AvInput
                    id="comment-datetime"
                    type="datetime-local"
                    className="form-control"
                    name="datetime"
                    value={isNew ? null : convertDateTimeFromServer(this.props.commentEntity.datetime)}
                  />
                </AvGroup>
                <AvGroup>
                  <Label for="sub.id">
                    <Translate contentKey="xprojectApp.comment.sub">Sub</Translate>
                  </Label>
                  <AvInput id="comment-sub" type="select" className="form-control" name="sub.id">
                    <option value="" key="0" />
                    {comments
                      ? comments.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label for="comments.id">
                    <Translate contentKey="xprojectApp.comment.comments">Comments</Translate>
                  </Label>
                  <AvInput id="comment-comments" type="select" className="form-control" name="comments.id">
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
                <Button tag={Link} id="cancel-save" to="/entity/comment" replace color="info">
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
  comments: storeState.comment.entities,
  products: storeState.product.entities,
  commentEntity: storeState.comment.entity,
  loading: storeState.comment.loading,
  updating: storeState.comment.updating,
  updateSuccess: storeState.comment.updateSuccess
});

const mapDispatchToProps = {
  getComments,
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
)(CommentUpdate);
