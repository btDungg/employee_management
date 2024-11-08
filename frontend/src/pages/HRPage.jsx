import { useParams } from 'react-router-dom';
import HRPoints from '../components/HRPoints.jsx';
import EmployeePoints from '../components/EmployeePoints.jsx';

const HRPage = () => {
    const { employeeId } = useParams();

    return (
        <div>
            <h1>HR Page</h1>
            <EmployeePoints employeeId={employeeId} />
            <HRPoints employeeId={employeeId} />
        </div>
    );
};

export default HRPage;