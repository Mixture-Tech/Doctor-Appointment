import { Suspense } from "react";
import loadable from "@loadable/component";
import AuthLayout from "./layouts/AuthLayout";
import BasicLayout from "./layouts/BasicLayout";
import { CircularProgress } from "@mui/material";
import { BrowserRouter, Routes, Route } from "react-router-dom";

const Home = loadable(() => import("./pages/Home/index"));
const Login = loadable(() => import("./pages/Auth/Login/index"));
const Register = loadable(() => import("./pages/Auth/Register/index"));
const ForgotPassword = loadable(() => import("./pages/Auth/ForgotPassword/index"));
const Specialities = loadable(() => import ("./pages/Specialities/index"));
const ListDoctor = loadable(() => import("./pages/Doctor/ListDoctors/index"));
const DoctorSchedule = loadable(() => import("./pages/Doctor/DoctorSchedule/index"));
const SpecialtyDetail = loadable(() => import("./pages/SpecialtyDetail/index"));

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
                        path="/lich-hen"
                        element={
                            <Suspense fallback={<CircularProgress />}>
                                <DoctorSchedule title="Lịch hẹn" />
                            </Suspense>
                        }
                    />
                    <Route
                        path="/chi-tiet-chuyen-khoa"
                        element={
                            <Suspense fallback={<CircularProgress />}>
                                <SpecialtyDetail title="Chi tiết chuyên khoa" />
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
            </Routes>
        </BrowserRouter>
    );
}

export default App;
