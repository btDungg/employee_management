import { useState } from 'react';
import PropTypes from 'prop-types';
import axios from 'axios';
import 'bootstrap/dist/css/bootstrap.min.css';
import { Modal, Button, Form } from 'react-bootstrap';

const IncreasePointsPopupHR = ({ show, handleClose, employee, onPointsIncreased }) => {
    const [points, setPoints] = useState('');

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            await axios.post('http://localhost:8080/api/points/increase', {
                id: employee.id,
                name: employee.name,
                point: Number(points),
                type: 'increase',
                managerId: employee.managerId
            });
            onPointsIncreased();
            handleClose();
        } catch (error) {
            console.error('Error increasing points:', error);
        }
    };

    return (
        <Modal show={show} onHide={handleClose}>
            <Modal.Header closeButton>
                <Modal.Title>Increase Points for {employee.name}</Modal.Title>
            </Modal.Header>
            <Modal.Body>
                <Form onSubmit={handleSubmit}>
                    <Form.Group controlId="formPoints">
                        <Form.Label>Points</Form.Label>
                        <Form.Control
                            type="number"
                            value={points}
                            onChange={(e) => setPoints(e.target.value)}
                            required
                        />
                    </Form.Group>
                    <Button variant="primary" type="submit">
                        Increase Points
                    </Button>
                </Form>
            </Modal.Body>
        </Modal>
    );
};

IncreasePointsPopupHR.propTypes = {
    show: PropTypes.bool.isRequired,
    handleClose: PropTypes.func.isRequired,
    employee: PropTypes.shape({
        id: PropTypes.number.isRequired,
        name: PropTypes.string.isRequired,
        managerId: PropTypes.number.isRequired
    }).isRequired,
    onPointsIncreased: PropTypes.func.isRequired,
};

export default IncreasePointsPopupHR;