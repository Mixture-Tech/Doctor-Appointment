import { Suspense } from "react";
import loadable from "@loadable/component";
import AuthLayout from "./layouts/AuthLayout";
import BasicLayout from "./layouts/BasicLayout";
import { CircularProgress } from "@mui/material";
import ChatbotLayout from "./layouts/ChatbotLayout";
// import { AuthProvider } from "./contexts/authContext";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import {ToastContainer} from "react-toastify";
import AdminLayout from "./layouts/AdminLayout";

const Home = loadable(() => import("./pages/Home/index"));
const Chatbot = loadable(() => import("./pages/Chatbot/index"));
const Login = loadable(() => import("./pages/Auth/Login/index"));
const Register = loadable(() => import("./pages/Auth/Register/index"));
const Specialities = loadable(() => import("./pages/Specialities/index"));
const ListDoctor = loadable(() => import("./pages/Doctor/ListDoctors/index"));
const SpecialtyDetail = loadable(() => import("./pages/SpecialtyDetail/index"));
const ForgotPassword = loadable(() => import("./pages/Auth/ForgotPassword/index"));
const DoctorSchedule = loadable(() => import("./pages/Doctor/DoctorSchedule/index"));
const ListAppointments = loadable(() => import("./pages/Appointment/ListAppointments/index"));
const AppointmentDetail = loadable(() => import("./pages/Appointment/AppointmentDetail/index"));
const AppointmentBooking = loadable(() => import("./pages/Appointment/AppointmentBooking/index"));
const AppointmentSuccessfully = loadable(() => import("./pages/Appointment/AppointmentSuccessfully/index"));
const DashboardAdmin = loadable(() => import("./pages/Admin/Dashboard/index"));
const ScheduleAdmin = loadable(() => import("./pages/Admin/Schedule/index"));
const EditSchedule = loadable(() => import("./pages/Admin/AppointmentDetail/index"));

function App() {
    return (
        <BrowserRouter>
            <Routes>
                <Route element={<AdminLayout />}>
                    <Route
                        path="/admin"
                        element={
                            <Suspense fallback={<CircularProgress />}>
                                <DashboardAdmin title="Trang chủ Admin" />
                            </Suspense>
                        }
                    />
                    <Route
                        path="/admin/quan-ly-lich-hen"
                        element={
                            <Suspense fallback={<CircularProgress />}>
                                <ScheduleAdmin title="Quản lý lịch hẹn" />
                            </Suspense>
                        }
                    />
                    <Route
                        path="/admin/chinh-sua-lich-hen/:appointmentId"
                        element={
                            <Suspense fallback={<CircularProgress />}>
                                <EditSchedule title="Chỉnh sửa lịch hẹn" />
                            </Suspense>
                        }
                    />
                </Route>
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
                        path="/danh-sach-chuyen-khoa"
                        element={
                            <Suspense fallback={<CircularProgress />}>
                                <Specialities title="Chuyên khoa" />
                            </Suspense>
                        }
                    />
                    <Route
                        path="/danh-sach-bac-si"
                        element={
                            <Suspense fallback={<CircularProgress />}>
                                <ListDoctor title="Danh sách bác sĩ" />
                            </Suspense>
                        }
                    />
                    <Route
                        path="/chi-tiet-bac-si/:id"
                        element={
                            <Suspense fallback={<CircularProgress />}>
                                <DoctorSchedule title="Lịch hẹn" />
                            </Suspense>
                        }
                    />
                    <Route
                        path="/chi-tiet-chuyen-khoa/:id"
                        element={
                            <Suspense fallback={<CircularProgress />}>
                                <SpecialtyDetail title="Chi tiết chuyên khoa" />
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
                        path="/danh-sach-lich-hen"
                        element={
                            <Suspense fallback={<CircularProgress />}>
                                <ListAppointments title="Danh sách lịch hẹn" />
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
                        path="/dat-lich"
                        element={
                            <Suspense fallback={<CircularProgress />}>
                                <AppointmentBooking title="Đặt lịch" />
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
                    <Route
                        path="/dang-ky"
                        element={
                            <Suspense fallback={<CircularProgress />}>
                                <Register title="Đăng ký" />
                            </Suspense>
                        }
                    />
                    <Route
                        path="/quen-mat-khau"
                        element={
                            <Suspense fallback={<CircularProgress />}>
                                <ForgotPassword title="Quên mật khẩu" />
                            </Suspense>
                        }
                    />
                </Route>
                <Route element={<ChatbotLayout />}>
                    <Route
                        path="/chat-bot"
                        element={
                            <Suspense fallback={<CircularProgress />}>
                                <Chatbot title="Chatbot" />
                            </Suspense>
                        }
                    />
                </Route>
            </Routes>
        </BrowserRouter>
    );
}

export default App;