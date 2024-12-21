import { Outlet, useLocation, useNavigate } from "react-router-dom";
import Sidebar from "../components/Form/Admin/Sidebar";
import Navbar from "../components/Form/Admin/Navbar";
import { useEffect, useState } from "react";
import { ToastContainer } from "react-toastify";

export default function AdminLayout() {
    const navigate = useNavigate();
    const location = useLocation();
    const [isSidebarOpen, setSidebarOpen] = useState(true);
    const [searchParams, setSearchParams] = useState({});

    const toggleSidebar = () => {
        setSidebarOpen(prevState => !prevState);
    };

    const handleSearch = (params) => {
        navigate("/admin/quan-ly-lich-hen");
        setSearchParams(params);
    };

    useEffect(() => {
        setSidebarOpen(true);
    }, []);

    return (
        <div className="flex flex-col min-h-screen">
            <div
                className={`transition-transform duration-300 ease-in-out ${isSidebarOpen ? 'ml-64' : 'ml-0'}`}
            >
                <Navbar 
                    toggleSidebar={toggleSidebar} 
                    onSearch={handleSearch}
                />
            </div>

            <div className="flex flex-1">
                <Sidebar isOpen={isSidebarOpen} />
                <div
                    className={`transition-transform duration-300 ease-in-out flex-1 p-4 ${isSidebarOpen ? 'ml-64' : 'ml-0'}`}
                >
                    <Outlet context={{ searchParams }} />
                </div>
            </div>
            <ToastContainer />
        </div>
    );
}