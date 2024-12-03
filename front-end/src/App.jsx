import { Suspense } from "react";
import loadable from "@loadable/component";
import AuthLayout from "./layouts/AuthLayout";
import BasicLayout from "./layouts/BasicLayout";
import { CircularProgress } from "@mui/material";
import { BrowserRouter, Routes, Route } from "react-router-dom";


const Home = loadable(() => import("./pages/Home/index"));
const Login = loadable(() => import("./pages/Auth/Login/index"));
const ListAppointments = loadable(() => import("./pages/Appointment/ListAppointments/index"));
const AppointmentDetail = loadable(() => import("./pages/Appointment/AppointmentDetail/index"));
const AppointmentBooking = loadable(() => import("./pages/Appointment/AppointmentBooking/index"));
const AppointmentSuccessfully = loadable(() => import("./pages/Appointment/AppointmentSuccessfully/index"));
const Specialities = loadable(() => import("./pages/Specialities/index"));

function App() {
    return (
        <BrowserRouter>
            <Routes>
                <Route element={<BasicLayout />}>
                    <Route
                        index
                        element={
                            <Suspense fallback={<CircularProgress />}>
                                <Home title="Trang chủ" />
                            </Suspense>
                        }
                    />
                    <Route
                        path="/dat-lich"
                        element={
                            <Suspense fallback={<CircularProgress />}>
                                <AppointmentBooking title="Đặt lịch" />
                            </Suspense>
                        }
                    />
                    <Route
                        path="/chi-tiet-lich-hen"
                        element={
                            <Suspense fallback={<CircularProgress />}>
                                <AppointmentDetail title="Chi tiết lịch hẹn" />
                            </Suspense>
                        }
                    />
                    <Route
                        path="/danh-sach-lich-hen"
                        element={
                            <Suspense fallback={<CircularProgress />}>
                                <ListAppointments title="Danh sách lịch hẹn" />
                            </Suspense>
                        }
                    />
                    <Route
                        path="/dat-lich-thanh-cong"
                        element={
                            <Suspense fallback={<CircularProgress />}>
                                <AppointmentSuccessfully title="Đặt lịch hẹn thành công" />
                            </Suspense>
                        }
                    />
                    <Route
                        path="/chuyen-khoa"
                        element={
                            <Suspense fallback={<CircularProgress />}>
                                <Specialities title="Chuyên Khoa" />
                            </Suspense>
                        }
                    />
                </Route>
                <Route element={<AuthLayout />}>
                    <Route
                        path="/dang-nhap"
                        element={
                            <Suspense fallback={<CircularProgress />}>
                                <Login title="Đăng nhập" />
                            </Suspense>
                        }
                    />
                </Route>
            </Routes>
        </BrowserRouter>
    );
}

export default App;
