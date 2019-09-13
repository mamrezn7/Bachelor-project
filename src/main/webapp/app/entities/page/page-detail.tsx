import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './page.reducer';
import { IPage } from 'app/shared/model/page.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IPageDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class PageDetail extends React.Component<IPageDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { pageEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="sampleMvnApp.page.detail.title">Page</Translate> [<b>{pageEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="title">
                <Translate contentKey="sampleMvnApp.page.title">Title</Translate>
              </span>
            </dt>
            <dd>{pageEntity.title}</dd>
            <dt>
              <span id="price">
                <Translate contentKey="sampleMvnApp.page.price">Price</Translate>
              </span>
            </dt>
            <dd>{pageEntity.price}</dd>
            <dt>
              <span id="details">
                <Translate contentKey="sampleMvnApp.page.details">Details</Translate>
              </span>
            </dt>
            <dd>{pageEntity.details}</dd>
            <dt>
              <Translate contentKey="sampleMvnApp.page.user">User</Translate>
            </dt>
            <dd>{pageEntity.userLogin ? pageEntity.userLogin : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/page" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/page/${pageEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ page }: IRootState) => ({
  pageEntity: page.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(PageDetail);
