import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IUser } from 'app/shared/model/user.model';
import { getUsers } from 'app/modules/administration/user-management/user-management.reducer';
import { getEntity, updateEntity, createEntity, reset } from './page.reducer';
import { IPage } from 'app/shared/model/page.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IPageUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IPageUpdateState {
  isNew: boolean;
  userId: string;
}

export class PageUpdate extends React.Component<IPageUpdateProps, IPageUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      userId: '0',
      isNew: !this.props.match.params || !this.props.match.params.id
    };
  }

  componentWillUpdate(nextProps, nextState) {
    if (nextProps.updateSuccess !== this.props.updateSuccess && nextProps.updateSuccess) {
      this.handleClose();
    }
  }

  componentDidMount() {
    if (!this.state.isNew) {
      this.props.getEntity(this.props.match.params.id);
    }

    this.props.getUsers();
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { pageEntity } = this.props;
      const entity = {
        ...pageEntity,
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
    this.props.history.push('/entity/page');
  };

  render() {
    const { pageEntity, users, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="sampleMvnApp.page.home.createOrEditLabel">
              <Translate contentKey="sampleMvnApp.page.home.createOrEditLabel">Create or edit a Page</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : pageEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="page-id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="page-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="titleLabel" for="page-title">
                    <Translate contentKey="sampleMvnApp.page.title">Title</Translate>
                  </Label>
                  <AvField
                    id="page-title"
                    type="text"
                    name="title"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') },
                      minLength: { value: 5, errorMessage: translate('entity.validation.minlength', { min: 5 }) }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="priceLabel" for="page-price">
                    <Translate contentKey="sampleMvnApp.page.price">Price</Translate>
                  </Label>
                  <AvField id="page-price" type="string" className="form-control" name="price" />
                </AvGroup>
                <AvGroup>
                  <Label id="detailsLabel" for="page-details">
                    <Translate contentKey="sampleMvnApp.page.details">Details</Translate>
                  </Label>
                  <AvField
                    id="page-details"
                    type="text"
                    name="details"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label for="page-user">
                    <Translate contentKey="sampleMvnApp.page.user">User</Translate>
                  </Label>
                  <AvInput id="page-user" type="select" className="form-control" name="userId">
                    <option value="" key="0" />
                    {users
                      ? users.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.login}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/page" replace color="info">
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
  users: storeState.userManagement.users,
  pageEntity: storeState.page.entity,
  loading: storeState.page.loading,
  updating: storeState.page.updating,
  updateSuccess: storeState.page.updateSuccess
});

const mapDispatchToProps = {
  getUsers,
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
)(PageUpdate);
