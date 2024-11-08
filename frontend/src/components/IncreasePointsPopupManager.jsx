import { useState, useEffect} from 'react';
import PropTypes from 'prop-types';
import { increasePointsByManager, getManagerBonusPointsById } from '../apis/api';
import 'bootstrap/dist/css/bootstrap.min.css';
import { Modal, Button, Form, Alert } from 'react-bootstrap';

const IncreasePointsPopupManager = ({ show, handleClose, employee, managerId, onPointsIncreased }) => {
    const [points, setPoints] = useState('');
    const [bonusPoints, setBonusPoints] = useState(0);
    const [errorMessage, setErrorMessage] = useState('');

    useEffect(() => {
        const fetchBonusPoints = async () => {
            try {
                const bonus = await getManagerBonusPointsById(managerId);
                setBonusPoints(bonus);
            } catch (error) {
                console.error('Error fetching bonus points:', error);
            }
        };

        if (show) {
            fetchBonusPoints();
        }
    }, [show, managerId]);

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const response = await increasePointsByManager(managerId, {
                id: employee.id,
                point: Number(points)
            });
            if (response === "Points have been increased successfully!") {
                onPointsIncreased();
                handleClose();
            } else {
                setErrorMessage(response);
            }
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
                <p>Current Bonus Points: {bonusPoints}</p>
                {errorMessage && <Alert variant="danger">{errorMessage}</Alert>}
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

IncreasePointsPopupManager.propTypes = {
    show: PropTypes.bool.isRequired,
    handleClose: PropTypes.func.isRequired,
    employee: PropTypes.shape({
        id: PropTypes.number.isRequired,
        name: PropTypes.string.isRequired
    }).isRequired,
    managerId: PropTypes.string.isRequired,
    onPointsIncreased: PropTypes.func.isRequired,
};

export default IncreasePointsPopupManager;