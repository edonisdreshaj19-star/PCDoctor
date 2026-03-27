using System;
using System.Collections.Generic;
using System.Text;

namespace PCDoctor.Core
{
    class SystemStats
    {
        public float CpuUsage { get; set; }
        public float AvailableMemoryMB { get; set; }
        public float TotalMemoryMB { get; set; }
        public float UsedMemoryMB { get; set; }

        public float RamUsage { get; set; }
    }
}
