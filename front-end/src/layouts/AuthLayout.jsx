import Login from '../pages/Auth/Login/index'

export default function AuthLayout() {
    // return !Cookies.get(StorageKeys.ACCESS_TOKEN) ? <Navigate to="/dang-nhap" replace /> : <Outlet />;
    return <Login />;
}
