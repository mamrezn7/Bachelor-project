import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './group.reducer';
import { IGroup } from 'app/shared/model/group.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IGroupDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class GroupDetail extends React.Component<IGroupDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { groupEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="sampleMvnApp.group.detail.title">Group</Translate> [<b>{groupEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="title">
                <Translate contentKey="sampleMvnApp.group.title">Title</Translate>
              </span>
            </dt>
            <dd>{groupEntity.title}</dd>
            <dt>
              <span id="details">
                <Translate contentKey="sampleMvnApp.group.details">Details</Translate>
              </span>
            </dt>
            <dd>{groupEntity.details}</dd>
            <dt>
              <Translate contentKey="sampleMvnApp.group.user">User</Translate>
            </dt>
            <dd>{groupEntity.userId ? groupEntity.userId : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/group" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/group/${groupEntity.id}/edit`} replace color="primary">
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

const mapStateToProps = ({ group }: IRootState) => ({
  groupEntity: group.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(GroupDetail);
