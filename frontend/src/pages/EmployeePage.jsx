import { useParams } from 'react-router-dom';
import EmployeePoints from '../components/EmployeePoints.jsx';

const EmployeePage = () => {
    const { employeeId } = useParams();

    return (
        <div>
            <h1>Employee Page</h1>
            <EmployeePoints employeeId={employeeId} />
        </div>
    );
};

export default EmployeePage;