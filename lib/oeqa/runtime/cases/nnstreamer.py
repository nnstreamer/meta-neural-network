import os
from subprocess import Popen, PIPE

from oeqa.runtime.case import OERuntimeTestCase
from oeqa.core.decorator.oeid import OETestID
from oeqa.core.decorator.oetimeout import OETimeout

class NNStreamerTest(OERuntimeTestCase):

    @OETestID(3001)
    def test_nnstreamer(self):
        cmd = 'export LD_LIBRARY_PATH=/usr/lib/gstreamer-1.0:$LD_LIBRARY_PATH; ' \
        '/usr/lib/nnstreamer/unittest/unittest_common'
        (status, output) = self.target.run(cmd)
        msg = " NNSTREAMER UNITTEST FAILED ( unittest_common ): %s" % output
        self.assertEqual(status, 0, msg=msg)

        cmd = 'export LD_LIBRARY_PATH=/usr/lib/gstreamer-1.0:$LD_LIBRARY_PATH; ' \
        '/usr/lib/nnstreamer/unittest/unittest_sink -d /usr/lib/nnstreamer/unittest --gst-plugin-path=/usr/lib/gstreamer-1.0'
        (status, output) = self.target.run(cmd)
        msg = " NNSTREAMER UNITTEST FAILED ( unittest_sink ): %s" % output
        self.assertEqual(status, 0, msg=msg)

        cmd = 'export LD_LIBRARY_PATH=/usr/lib/gstreamer-1.0:$LD_LIBRARY_PATH; ' \
        '/usr/lib/nnstreamer/unittest/unittest_plugins --gst-plugin-path=/usr/lib/gstreamer-1.0'
        (status, output) = self.target.run(cmd)
        msg = " NNSTREAMER UNITTEST FAILED ( unittest_plugins ): %s" % output
        self.assertEqual(status, 0, msg=msg)


