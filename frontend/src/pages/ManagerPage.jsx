import { useParams } from 'react-router-dom';
import ManagerPoints from '../components/ManagerPoints.jsx';
import EmployeePoints from '../components/EmployeePoints.jsx';

const ManagerPage = () => {
    const { employeeId } = useParams();

    return (
        <div>
            <h1>Manager Page</h1>
            <EmployeePoints employeeId={employeeId} />
            <ManagerPoints employeeId={employeeId} />
        </div>
    );
};

export default ManagerPage;