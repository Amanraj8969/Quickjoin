import './home.scss';

import React,{useState,useEffect} from 'react';
import { Link ,useLocation,useNavigate} from 'react-router-dom';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { Row, Col, Alert } from 'reactstrap';

import { useAppSelector } from 'app/config/store';
import { useAppDispatch} from 'app/config/store';
import { getEntities } from 'app/entities/classes/classes.reducer';
import { getSortState } from 'react-jhipster';

import {ASC, DESC,SORT } from 'app/shared/util/pagination.constants';
import { overrideSortStateWithQueryParams } from 'app/shared/util/entity-utils';


export const Home = () => {
  const dispatch=useAppDispatch();
  const pageLocation = useLocation();
  const navigate = useNavigate();
  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));
  const classlist=useAppSelector(state=>state.classes.entities);
  const [loading, setLoading] = useState(true);
  

  useEffect(() => {
    dispatch(getEntities({
      sort: `${sortState.sort},${sortState.order}`,
    })).then(response => {
      console.log("----------------------------------------",response); // Log the response data
      setLoading(false);
    }).catch(error => {
      console.error('Error fetching entities:', error);
      setLoading(false);
    });
  }, [dispatch]);




  

  const account = useAppSelector(state => state.authentication.account);

  

  return (
  <>
  <div style={{width:"100%",textAlign:"end",marginBottom:"20px"}}>
  <Link to="/classes/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Classes
          </Link>
  </div>
    <Row>
    {classlist.map((item, index) => (
      <Col key={index} md="4" className="mb-4">
        <div className="card">
          <div className="card-body">
            <h5 className="card-title">{item.title}</h5>
            <p className="card-text">Description: {item.description}</p>
            <p className="card-text">Teacher Name: {item.techer_name}</p>
            <p className="card-text">Duration: {item.duration}</p>
            <p className="card-text">Location: {item.location}</p>
            <p className="card-text">Price: {item.price}</p>
          </div>
          <a href="https://buy.stripe.com/test_3cs8wWe8y97s5LabIL"><button>Enroll now</button></a>
        </div>
      </Col>
    ))}
  </Row>
  </>

  
  );
};

export default Home;
