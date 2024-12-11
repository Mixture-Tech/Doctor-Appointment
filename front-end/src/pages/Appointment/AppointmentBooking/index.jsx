import React, { useState } from 'react';
import Appointment from './components/Appointment';
import DoctorReferral from './components/Referral';

export default function AppointmentBooking() {
  return (
    <>
      <DoctorReferral />
      <Appointment />

    </>
  );
}



// import React from "react";
// import Appointment from "./components/Appointment";
// import DoctorReferral from "./components/Referral";

// export default function AppointmentBooking() {
//     return (
//         <div className="flex justify-center gap-4 px-4 py-6 bg-gray-100">
//             {/* Referral Section */}
//             <div className="w-1/3 bg-white rounded-lg shadow-md p-4">
//                 <DoctorReferral />
//             </div>

//             {/* Appointment Section */}
//             <div className="w-2/3 bg-white rounded-lg shadow-md p-4">
//                 <Appointment />
//             </div>
//         </div>
//     );
// }
