import React, { useContext } from 'react';
import { Navigate } from 'react-router-dom';
import { jwtDecode } from 'jwt-decode';
import { UserContext } from '../components/personal_information/UserProvider.jsx';

const PrivateRoute = ({ element: Component, requiredRoles, ...rest }) => {
    const { user } = useContext(UserContext);
    const token = localStorage.getItem('token');
    let isAuthenticated = false;

    if (token) {
        try {
            const decodedToken = jwtDecode(token);
            const currentTime = Date.now() / 1000;
            isAuthenticated = decodedToken.exp > currentTime;
        } catch (error) {
            console.error('Invalid token:', error);
        }
    }

    if (!isAuthenticated) {
        return <Navigate to="/login" />;
    }

    if (requiredRoles && !requiredRoles.includes(user.type)) {
        return <div>You do not have permission to view this page.</div>;
    }

    return <Component {...rest} />;
};

export default PrivateRoute;