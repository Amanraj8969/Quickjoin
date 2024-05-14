import './home.scss';

import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { Row, Col, Alert } from 'reactstrap';

import { useAppSelector } from 'app/config/store';
import { useAppDispatch } from 'app/config/store';
import { getEntities } from 'app/entities/classes/classes.reducer';
import { getSortState } from 'react-jhipster';

import { ASC, DESC, SORT } from 'app/shared/util/pagination.constants';
import { overrideSortStateWithQueryParams } from 'app/shared/util/entity-utils';
import { loadStripe } from '@stripe/stripe-js';
import axios from 'axios';

const stripePromise = loadStripe(
  'pk_test_51MPm5BSJseISqjDOE7xwboYQklJRXm643aTG9iWLz2arlx8Jlht98kDJugBODmlH1IZqVAC9psQTKuhtufCFe9UK00kKBp4iDZ',
);
export const Home = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();
  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));
  const classlist = useAppSelector(state => state.classes.entities);
  const [loading, setLoading] = useState(true);
  const [errorMessage, setErrorMessage] = useState('');

  const getBearerToken = () => {
    var authToken = localStorage.getItem('jhi-authenticationToken') || sessionStorage.getItem('jhi-authenticationToken');
    if (authToken) {
      authToken = JSON.parse(authToken);
      return `Bearer ${authToken}`;
    }
    return null;
  };

  useEffect(() => {
    dispatch(
      getEntities({
        sort: `${sortState.sort},${sortState.order}`,
      }),
    )
      .then(response => {
        console.log('----------------------------------------', response); // Log the response data
        setLoading(false);
      })
      .catch(error => {
        console.error('Error fetching entities:', error);
        setLoading(false);
      });
  }, [dispatch]);

  const handleEnroll = async itemId => {
    const API_URL = 'http://localhost:9000/api/create-payment-intent'; // Update the API URL

    try {
      const response = await axios.get(API_URL, {
        params: { itemId },
        headers: {
          Authorization: getBearerToken(),
        },
      });

      // Process the response as needed
      console.log('API Response:', response.data);
      const paymentIntent = response.data;
    } catch (error) {
      console.error('Error:', error.message);
      // Handle error
    }
  };
  const account = useAppSelector(state => state.authentication.account);

  return (
    <>
      <div style={{ width: '100%', textAlign: 'end', marginBottom: '20px' }}>
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

              <button onClick={() => handleEnroll(item.id)}>Enroll now</button>
            </div>
          </Col>
        ))}
      </Row>
    </>
  );
};

export default Home;
