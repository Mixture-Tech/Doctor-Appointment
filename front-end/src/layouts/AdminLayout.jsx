import Footer from "../components/Footer/index";
import { Outlet, useLocation } from "react-router-dom";

export default function AdminLayout() {
    const location = useLocation();

    return (
        <div className="flex flex-col min-h-screen">
            <Outlet />
        </div>
    );
}
