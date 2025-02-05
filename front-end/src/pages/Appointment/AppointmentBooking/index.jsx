import React, { useState } from 'react';
import Appointment from './components/Appointment';
import DoctorReferral from './components/Referral';
import { ToastContainer } from 'react-toastify';

export default function AppointmentBooking() {
  return (
    <div>
      <ToastContainer style={{ marginTop: '50px' }} />
      <DoctorReferral />
      <Appointment />
    </div>
  );
}
